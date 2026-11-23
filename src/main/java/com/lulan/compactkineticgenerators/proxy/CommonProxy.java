package com.lulan.compactkineticgenerators.proxy;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class CommonProxy implements IProxy {

    // simple network
    public static final String channelNameS = "ckgSync";

    public static SimpleNetworkWrapper channelS;

    @Override
    public void registerChannel() {}

}
