package com.pichulacorp.integracion.Security;

import java.util.Arrays;
import java.util.List;

public enum Roles {
    Admin(Privileges.CustomerRegister),
    CustomerBasico(Privileges.ReporteSimpleMensual),
    CustomerMedio(Privileges.ReporteSimpleSemanal,Privileges.ReporteDetalladoSemanal),
    CustomerFull(Privileges.ReporteDetalladoSemanal,Privileges.ReporteSimpleSemanal,Privileges.ReporteGeneralMensual);

    public final List<Privileges> privilegesList;

    Roles(Privileges... privilegeList){
        privilegesList = Arrays.asList(privilegeList);
    }


    @Override
    public String toString() {
        return "ROLE_"+name();
    }
}
