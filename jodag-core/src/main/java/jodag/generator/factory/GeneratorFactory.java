package jodag.generator.factory;

import jodag.exception.GeneratorException;
import jodag.generator.Generator;
import jodag.generator.registrable.RegistrableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code GeneratorFactory} is the core class of the jodag-core library. <br>
 * It provides access to default Generators via {@code as~} methods
 * defined in the implemented factory interfaces ({@link PrimitiveFactory},
 * {@link CommonFactory}, {@link ExtendedFactory}). <br>
 * <p>
 * Additionally, it manages {@link RegistrableGenerator} instances
 * that can be registered and retrieved dynamically by key or path.
 * </p>
 */
public class GeneratorFactory implements PrimitiveFactory, CommonFactory, ExtendedFactory {

    private static final Map<String, RegistrableGenerator<?>> registrableGenerators = new ConcurrentHashMap<>();

    /**
     * Retrieves the {@link RegistrableGenerator} for the given key. <br>
     * If RegistrableGenerator isn't exists, create new one with key, path, type. <br>
     * then return it.
     * @param key  unique identifier of the RegistrableGenerator
     * @param path  the path of the file used as a source of RegistrableGenerator
     * @param type  type of the data the RegistrableGenerator generates.
     * @param <T> the type parameter of the Generator
     * @return {@link Generator}
     */
    @SuppressWarnings("unchecked")
    public <T> Generator<T> getRegistrableGenerator(String key, String path, Class<T> type) {
        RegistrableGenerator<T> generator = (RegistrableGenerator<T>) registrableGenerators.get(key);
        if(generator == null) {
            generator = new RegistrableGenerator<>(key, path, type);
            registrableGenerators.put(key, generator);
        }
        return generator;
    }

    /**
     * Retrieves the {@code RegistrableGenerator} by the given key parameter, identifier of Generator. <br>
     *
     * @param key  unique identifier of the {@code RegistrableGenerator}
     * @param <T> the type parameter of the Generator
     * @return the {@code Generator} registered under the given key.
     * @throws GeneratorException if no generator is found for the given key
     */
    @SuppressWarnings("unchecked")
    public <T> Generator<T> getRegistrableGeneratorByKey(String key) {
        RegistrableGenerator<T> generator = (RegistrableGenerator<T>) registrableGenerators.get(key);
        if(generator == null) {
            throw new GeneratorException("No generator for key: " + key);
        }
        return generator;
    }

    /**
     * Retrieves {@code RegistrableGenerator} by the given path parameter.
     * @param path the file path used as a source for the generator (must be unique)
     * @return the {@code Generator} registered under the given path.
     * @throws GeneratorException If couldn't find the Generator.
     */
    public Generator<?> getRegistrableGeneratorByPath(String path) {
        for (RegistrableGenerator<?> generator : registrableGenerators.values()) {
            if(generator.getPath().equals(path)) {
                return generator;
            }
        }
        throw new GeneratorException("Cannot found Generator for path");
    }

    /**
     * Adds {@code RegistrableGenerator}
     * @param key unique identifier of the {@code RegistrableGenerator}
     * @param path the file path used as a source for the generator (must be unique)
     * @param type type of the data the RegistrableGenerator generates
     * @param <T> the type parameter of the Generator
     * @return the newly created {@code Generator}
     * @throws GeneratorException if a generator with the same key or path already exists
     */
    public <T> Generator<T> addRegistrableGenerator(String key, String path, Class<T> type) {
        // check path duplication
        for (RegistrableGenerator<?> existed : registrableGenerators.values()) {
            if(existed.getPath().equals(path)) {
                throw new GeneratorException("Duplicate path : " + path);
            }
        }

        RegistrableGenerator<T> created = new RegistrableGenerator<>(key, path, type);
        Generator<?> generator = registrableGenerators.putIfAbsent(key, created);
        if(generator != null) {
            throw new GeneratorException("Duplicate key: " + key);
        }
        return created;
    }

    /**
     * Removes all registered {@code RegistrableGenerator} instances. <br>
     * After calling this method, new generators must be registered.
     */
    public void clearRegistrableGenerator() {
        registrableGenerators.clear();
    }

    /**
     * returns all the names of {@code RegistrableGenerator} instances.
     * @return a list of generator names.
     */
    public List<String> getRegistrableGeneratorNames() {
        return new ArrayList<>(registrableGenerators.keySet());
    }

    /**
     *
     * @param key
     * @return
     */
    public Boolean existsRegistrableGenerator(String key) {
        return registrableGenerators.containsKey(key);
    }
}
