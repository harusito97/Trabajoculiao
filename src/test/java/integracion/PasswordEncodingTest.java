package integracion;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class PasswordEncodingTest {

    @Test
    public void probarUnaWea(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String unaweaencodea = bCryptPasswordEncoder.encode("unawea");
        System.out.println(unaweaencodea);

    }
}