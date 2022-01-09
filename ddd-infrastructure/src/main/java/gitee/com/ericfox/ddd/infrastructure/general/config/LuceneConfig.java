package gitee.com.ericfox.ddd.infrastructure.general.config;

import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.FileUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class LuceneConfig {
    @Resource
    private ServiceProperties serviceProperties;

    @Bean
    public Analyzer analyzer() {
        return new IKAnalyzer();
    }

    @Bean
    @SneakyThrows
    public Directory directory() {
        String rootPath = serviceProperties.getRepoStrategy().getLucene().getRootPath();
        if (!FileUtil.isDirectory(rootPath)) {
            FileUtil.touch(rootPath);
        }
        Path path = Paths.get(rootPath);
        return FSDirectory.open(path);
    }

    @Bean
    @SneakyThrows
    public IndexWriter indexWriter() {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(directory(), indexWriterConfig);
        if (serviceProperties.getRepoStrategy().getLucene().getClearWhenStart()) {
            indexWriter.deleteAll();
            indexWriter.commit();
        }
        return indexWriter;
    }

    @Bean
    @SneakyThrows
    public SearcherManager searcherManager(Directory directory, IndexWriter indexWriter) {
        SearcherManager searcherManager = new SearcherManager(indexWriter, false, false, new SearcherFactory());
        ControlledRealTimeReopenThread<IndexSearcher> controlledRealTimeReopenThread = new ControlledRealTimeReopenThread(indexWriter, searcherManager, 5.0, 0.025);
        controlledRealTimeReopenThread.setDaemon(true);
        controlledRealTimeReopenThread.setName("更新IndexReader线程");
        controlledRealTimeReopenThread.start();
        return searcherManager;
    }

    private String buildDirectoryPath(String rootPathName) {
        String luceneLocalIndexDirectoryPath = serviceProperties.getRepoStrategy().getLucene().getRootPath();
        if (StrUtil.endWith(luceneLocalIndexDirectoryPath, File.separator)) {
            return luceneLocalIndexDirectoryPath + rootPathName;
        }
        return luceneLocalIndexDirectoryPath + File.separator + rootPathName;
    }
}
