package gitee.com.ericfox.ddd.starter.cloud.listener;

import org.apache.curator.framework.CuratorFramework;

public class ZookeeperListener {
    private final CuratorFramework client;

    public ZookeeperListener(CuratorFramework client) {
        this.client = client;
    }

    public void zNodeListener() {
        //FIXME
    }

    public void zNodeChildrenListener() {
        //FIXME
    }
}
