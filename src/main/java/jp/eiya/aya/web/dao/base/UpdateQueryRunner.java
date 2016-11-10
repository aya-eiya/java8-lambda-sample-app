package jp.eiya.aya.web.dao.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.escape.Escapers;

import java.util.Map;

public abstract class UpdateQueryRunner {

    private interface TryTo<T>{
        T what() throws Throwable;
    }
    private interface OnThrow<T>{
        T returnAs();
    }

    private static <T> T tryWithRescue(TryTo<T> tryTo, OnThrow<T> rescue){
        try{return tryTo.what();}catch(Throwable th){return rescue.returnAs();}
    }
    private static <T> T tryWithRescue(TryTo<T> tryTo1, TryTo<T> tryTo2, OnThrow<T> defaultCase){
        try{return tryTo1.what();}catch(Throwable th){return tryWithRescue(tryTo2,defaultCase);}
    }

    protected abstract static class DAOQueryFailedException extends RuntimeException{
        public DAOQueryFailedException(String query,Exception exception){
            super(query,exception);
        }
    }

    public interface QueryResult{
        boolean isSuccess();
        void reportException() throws DAOQueryFailedException;
    }

    protected static class UpdateQueryResult implements QueryResult{

        private static class DAOUpdateQueryFailedException extends DAOQueryFailedException{
            public DAOUpdateQueryFailedException(UpdateQueryResult result){
                super(String.format("{query:\"%s\",params:%s}",
                        Escapers.builder().addEscape('"',"\\\"").build().escape(result.query),
                        tryWithRescue(
                            ()-> new ObjectMapper().writeValueAsString(result.params),
                            ()-> result.params.toString(),
                            ()-> null
                        )
                ),result.exception);
            }
        }

        private final boolean success;
        private final String query;
        private final Exception exception;
        private final Map<String,Object> params;
        private final int UpdateRowSize;

        public UpdateQueryResult(
                String query,
                int updateRowSize
        ){
            this(query,(Exception) null,updateRowSize);
        }

        public UpdateQueryResult(
                String query,
                Exception exception,
                int updateRowSize
        ){
            this(query, ImmutableMap.of(),exception,updateRowSize);
        }
        public UpdateQueryResult(
                String query,
                Map<String,Object> params,
                int updateRowSize
        ){
            this(query, params,null,updateRowSize);
        }

        public UpdateQueryResult(
                String query,
                Map<String,Object> params,
                Exception exception,
                int updateRowSize
        ){
            this(exception==null,query,params,exception,updateRowSize);
        }

        public UpdateQueryResult(
                boolean success,
                String query,
                Map<String,Object> params,
                Exception exception,
                int updateRowSize
        ){
            this.success = success;
            this.query = query;
            this.params = params;
            this.exception = exception;
            this.UpdateRowSize = updateRowSize;
        }

        public boolean isSuccess(){
            return success;
        }

        public void reportException(){
            if(exception==null){ return; }
            throw new DAOUpdateQueryFailedException(this);
        }
    }
}
