package ru.haazad.cloud.client.factory;

import ru.haazad.cloud.client.service.impl.NettyNetworkService;

public class Factory {

    public static NettyNetworkService getNetworkService() {
        return NettyNetworkService.getNetwork();
    }
}
