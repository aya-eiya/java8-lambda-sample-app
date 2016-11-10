package jp.eiya.aya.web.service.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import jp.eiya.aya.web.dao.CommonDAO;
import org.unbescape.json.JsonEscape;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;


public abstract class ServiceBase {

    interface  ifPresent<T,S extends Optional<T>>{
        T map();
    }



    @Inject
    protected CommonDAO myDAO;

}
