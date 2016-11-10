package jp.eiya.aya.web.util.security;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class HashUtils {

    public String salt() {
        return "";
    }

    public String hash(String password, String salt) {
        return null;
    }
}
