package ru.haazad.cloud.client.factory;

import ru.haazad.cloud.client.service.EncryptPasswordService;
import ru.haazad.cloud.client.service.impl.EncryptPasswordServiceMD5;
import ru.haazad.cloud.client.service.impl.NettyNetworkService;

public class Factory {

    public static NettyNetworkService getNetworkService() {
        return NettyNetworkService.getNetwork();
    }

    public static EncryptPasswordService getEncryptService() {
        return EncryptPasswordServiceMD5.getEncryptService();
    }
}
