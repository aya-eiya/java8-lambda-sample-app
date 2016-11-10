package jp.eiya.aya.web.util.rest;

public interface APIResponse<T> extends ContentResult<T>{
    default boolean success() {return true;}
    default String message(){return "request finished successfully.";}

    static <T> APIResponse<T> failBy(String message,ContentResult<T> contentResult){
        return new APIResponse<T>() {
            public boolean success(){ return false;}
            public String message(){ return message;}
            public T getResult() { return contentResult.getResult();}
        };
    }
}
