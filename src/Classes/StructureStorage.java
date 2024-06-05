package Classes;

import Server.Network.PostgresManager;
import lombok.Getter;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


/**
 * Класс, отвечающий за работу с коллекцией.
 * Сохраняется автоматически почти всегда (Не сохранится, если выйти через exit)
 */

public class StructureStorage implements Cleaner.Cleanable {

    // очев
    @Getter
    protected Stack<Flat> collection = new Stack<>();
    private ServerContext serverContext;
    private PostgresManager manager;
    protected ReentrantLock lock;

    public StructureStorage(ServerContext serverContext) {
        this.serverContext = serverContext;
        this.manager = new PostgresManager(serverContext);
        this.lock = new ReentrantLock();
    }

    public synchronized void sort() {
        collection.sort(null);
    }


    public void load() { // Запрашивает загрузку коллекции у файлового менеджера
        try {
            manager.loadCollectionFromDB();
            // collection.addAll((Collection<? extends Flat>) serverContext.getFileManager().loadCollection());
        } catch ( SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Something went wrong during loading of collection");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean removeFlatById(Integer id, int userId) {
        lock.lock();
        try {
            if (manager.deleteById(id, userId) < 1){
                return false;
            }
            for (Flat flat : collection) {
                if (Objects.equals(flat.getId(), id)) {
                    flat.markForDeletion();
                    collection.remove(flat);
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }

        return false;
    }

    public synchronized boolean removeLastFlat() {
        lock.lock();
        try {
            Flat f = collection.peek();
            return removeFlatById(f.getId(), f.getCreator_id());
        } finally {
            lock.unlock();
        }
    }

    public synchronized boolean removeFlatAt(int pos) {
        lock.lock();
        try {
            Flat f = collection.elementAt(pos);
            return removeFlatById(f.getId(), f.getCreator_id());
        } finally {
            lock.unlock();
        }
    }

    public synchronized boolean addFlat(Flat f, int userId) {
        lock.lock();
        try {
            int id = manager.addNewFlat(f.getUpdateRecord(), userId);
            if (id > 0) {
                f.setId(id);
                return collection.add(f);
            };
            return false;
        } finally {
            lock.unlock();
        }

    }
    public boolean loadFlat(Flat f) {
        return collection.add(f);
    }

    public int getSize() {
        return collection.size();
    }

    public Flat getFlatById(int id) {
        Flat ans = null;
        for (Flat flat : collection) {
            if (Objects.equals(flat.getId(), id)) {
                ans = flat;
                break;
            }
        }
        return ans;
    }

    public synchronized void clearCollection(int userId) {
        lock.lock();
        try {
            manager.deleteByUserId(userId);
            Stream<Flat> s = collection.stream().filter((f) -> {return (f.getCreator_id() != userId);});
            collection.clear();
            s.forEach(collection::push);
        } finally {
            lock.unlock();
        }
    }

    public int getNumberOfRoomsSum() {
        int cnt = 0;
        for (Flat f : collection) {
            cnt += f.getNumberOfRooms();
        }
        return cnt;
    }

    public int countLTFurnish(int amount) {
        int res = 0;
        for (Flat f : collection) {
            if (f.getFurnish().ordinal() < amount) res++;
        }
        return res;
    }

    public ArrayList<House> getUniqueHouse() {
        ArrayList<House> ans = new ArrayList<>();
        for (Flat f : collection) {
            if (!ans.contains(f.getHouse())) {
                ans.add(f.getHouse());
            }
        }
        return ans;
    }

    @Override
    public void clean() {
        if (!serverContext.isExitCommandUsed()) {
            serverContext.getFileManager().saveCollection();
        }
    }


    @Getter
    private final Runnable onCleanRunnable = new Runnable() {
        /**
         Прикол, благодаря которому, коллекция сохранится в случае неожиданностей (В т.ч. ctrl+c)
         */
        @Override
        public void run() {
            clean();
        }
    };


    public synchronized boolean updateFlatByRecord(SendedFlatUpdateRecord arg, int userId) {
        lock.lock();
        try {
            if (manager.updateById(arg.id(), arg.flatUpdateRecord(), userId) < 1){
                return false;
            }
            for (Flat flat : collection) {
                if (Objects.equals(flat.getId(), arg.id())) {
                    flat.update(arg.flatUpdateRecord());
                    return true;
                }
            }
        }finally {
            lock.unlock();
        }

        return false;
    }
}
