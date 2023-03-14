package qtx.cloud.other.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 抽象工厂模式
 *
 * @author qtx
 * @since 2023/3/14 10:53
 */
public class AbstractFactory {

    /**
     * product: Keyboard
     */
    interface Keyboard {
        /**
         * print out
         */
        void print();
    }

    /**
     * rog Keyboard
     */
    static class RogKeyboard implements Keyboard {

        @Override
        public void print() {
            System.out.println("Create ROG Keyboard");
        }
    }

    /**
     * Razer Keyboard
     */
    static class RazerKeyboard implements Keyboard {

        @Override
        public void print() {
            System.out.println("Create Razer Keyboard");
        }
    }

    /**
     * product: Factory
     */
    interface ProductFactory {
        /**
         * create Keyboard
         *
         * @return Keyboard
         */
        Keyboard createKeyboard();
    }

    static class RogFactory implements ProductFactory {

        @Override
        public Keyboard createKeyboard() {
            return new RogKeyboard();
        }
    }

    static class RazerFactory implements ProductFactory {

        @Override
        public Keyboard createKeyboard() {
            return new RazerKeyboard();
        }
    }

    /**
     * init
     */
    static class Application {

        private final Keyboard keyboard;

        public Application(ProductFactory factory) {
            this.keyboard = factory.createKeyboard();
        }

        private void print() {
            keyboard.print();
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextBoolean() ? 1 : 0;
        getFactory(ProductEnum.PRODUCT.get(i)).print();
    }

    /**
     * @param product factory type
     * @return app
     */
    private static Application getFactory(ProductEnum product) {
        ProductFactory productFactory;
        if (ProductEnum.ROG.equals(product)) {
            productFactory = new RogFactory();
        } else {
            productFactory = new RazerFactory();
        }
        return new Application(productFactory);
    }

    enum ProductEnum {
        /**
         * product:ROG
         */
        ROG,
        /**
         * product:Razer
         */
        RAZER;

        private static final List<ProductEnum> PRODUCT = new ArrayList<>(10);

        static {
            PRODUCT.addAll(Arrays.asList(ProductEnum.values()));
        }
    }

}
