package com.pichulacorp.integracion.Security;

import java.util.Arrays;
import java.util.List;

public enum Roles {
    Admin(Privileges.values()),
    Basic(Privileges.genericOperations, Privileges.SimpleReport),
    Medium(Privileges.genericOperations, Privileges.SimpleReport),
    Full(Privileges.genericOperations, Privileges.SimpleReport);

    public final List<Privileges> privilegesList;

    Roles(Privileges... privilegeList){
        privilegesList = Arrays.asList(privilegeList);
    }


    @Override
    public String toString() {
        return "ROLE_"+name();
    }
}
