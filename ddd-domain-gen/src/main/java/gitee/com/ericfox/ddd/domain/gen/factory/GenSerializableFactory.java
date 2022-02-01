package gitee.com.ericfox.ddd.domain.gen.factory;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.XmlUtil;

import java.io.File;

public abstract class GenSerializableFactory {
    /**
     * 从文件反序列化
     */
    public abstract <T> T deserialization(File xmlFile);

    /**
     * 序列化xml数据
     */
    public abstract <T> void serialization(File dest, T bean);

    public static GenSerializableFactory getDefaultInstance() {
        return new GenSerializableFactory() {
            @Override
            public <T> T deserialization(File xmlFile) {
                return XmlUtil.readObjectFromXml(xmlFile);
            }

            @Override
            public <T> void serialization(File dest, T bean) {
                XmlUtil.writeObjectAsXml(dest, bean);
            }
        };
    }
}