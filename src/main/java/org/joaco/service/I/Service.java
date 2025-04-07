package org.joaco.service.I;

import java.util.List;

public interface Service <T>{

    public void save(T t);
    public T searchById(int id);
    public List<T> searchAll();
    public void update(T t);
    public void delete(int id);


}
