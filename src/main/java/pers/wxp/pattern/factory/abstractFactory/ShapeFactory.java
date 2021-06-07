package pers.wxp.pattern.factory.abstractFactory;

/**
 * @author xiaopeng
 * @date 2021年05月21日 9:30
 * @description
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    Shape getShape(String shape) {
        if (shape == null) {
            return null;
        }

        shape = shape.toLowerCase();

        switch (shape) {
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            case "square":
                return new Square();
            default:
                return null;
        }
    }

    @Override
    Color getColor(String color) {
        return null;
    }
}
