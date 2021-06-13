package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;

import java.util.ArrayList;
import java.util.List;

public class CredentialFactory {
    public static List<Credential> createListOfCredential() {
        var credential = new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );
        var list = new ArrayList<Credential>();
        list.add(credential);
        return list;
    }

    public static Credential createCredentialWithCredentialId() {
        return new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );
    }

    public static Credential createCredentialWithoutCredentialId() {
        return new Credential(null,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );
    }

}
