package jp.eiya.aya.web.dao.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import jp.eiya.aya.web.model.dao.base.DAOModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.unbescape.json.JsonEscape;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractDAO extends UpdateQueryRunner{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract EntityManager getEntityManager();
    private class Counter{
        private int count = 0;
        public int countUp(){return (count++);}
        public int getCurrent(){return count;}
    }

    private <T> void forEach(Collection<T> items, ForEachLoop<T> forEachLoop){
        forEach(items.iterator(),forEachLoop);
    }

    private <T> void forEach(Iterator<T> items, ForEachLoop<T> forEachLoop){
        int index = 0;
        while(items.hasNext()){
            forEachLoop.loop(index++,items.next());
        }
    }

    public interface ModelBuilder<T extends DAOModel>{
        T build();
    }

    public QueryResult insert(ModelBuilder<?> modelBuilder){ return insert(modelBuilder.build());}
    public QueryResult update(ModelBuilder<?> modelBuilder){ return update(modelBuilder.build());}
    public QueryResult upsert(ModelBuilder<?> modelBuilder){ return upsert(modelBuilder.build());}

    public QueryResult insert(DAOModel model){
        try {
            getEntityManager().persist(model);
            return new UpdateQueryResult("AbstractDAO#insert", BeanMap.create(model), 1);
        }catch(EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            return new UpdateQueryResult("AbstractDAO#insert", BeanMap.create(model), e, 0);
        }
    }

    public QueryResult upsert(DAOModel model){
        QueryResult insertResult = insert(model);
        if(!insertResult.isSuccess()){
            return update(model);
        }
        return insertResult;
    }

    public QueryResult update(DAOModel model){
        try {
            getEntityManager().merge(model);
            return new UpdateQueryResult("AbstractDAO#update", BeanMap.create(model), 1);
        }catch(EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            return new UpdateQueryResult("AbstractDAO#update", BeanMap.create(model), e, 0);
        }
    }

    private interface ForEachLoop<T> {
        public void loop(int index, T key);
    }

    protected <T extends DAOModel,S> List<T> find(Class<T> modelClass,String query,Map<String,S> params){
        String paramStr = query;
        LinkedList<String> keys = new LinkedList<>(params.keySet());
        if(paramStr != null) {
            int index=0;
            for(String key : keys){
                paramStr = paramStr.replaceAll("\\$" + key,"?");
            }
        }
        String where = paramStr.toLowerCase().startsWith("where ")?"":"where";

        String queryStr = String.format("select c from %s c %s %s",modelClass.getSimpleName(),where,paramStr);
        TypedQuery<T> tdQuery = getEntityManager().createQuery(queryStr,modelClass);
        forEach(keys,(index,key)->{
            tdQuery.setParameter(index++,params.get(key));
        });
        return ImmutableList.copyOf(tdQuery.getResultList());
    }
}
