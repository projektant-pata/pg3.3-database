package cz.spse.bajer.facade;

public interface FacadeInterface<T> {
    boolean create(T obj);
    T read(int id);
    boolean update(int id, T obj);
    boolean delete(int id);
}