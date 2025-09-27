package jodag.generator.registrable;

import jodag.PathResourceLoader;
import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;

/**
 * DataRegistry에 등록해서 사용하는 Generator,
 * 각 인스턴스틑 파일 경로를 가지며, get메서드를 통해 파일의 데이터에 랜덤 접근하여 값을 return함
 * @param <T>
 */
public class RegistrableGenerator<T> extends AbstractGenerator<T> {

    private final List<T> data;
    private final String path;

    @SuppressWarnings("unchecked")
    public RegistrableGenerator(String key, String resourcePath, Class<T> type) {
        super(key, type);
        this.path = resourcePath;
        InputStream is = PathResourceLoader.getPath(resourcePath);
        this.data = (List<T>) new BufferedReader(new InputStreamReader(is))
                .lines().toList();
    }

    @Override
    public T get() {
        return data.get(randomProvider.getInt(data.size()));
    }

    public String getPath() {
        return path;
    }
}
