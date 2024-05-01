package Task6;

public class CheckNext {
    private Pair origin;
    private Pair position;

    public CheckNext() {
        this.origin = new Pair();
        this.position = new Pair();
    }

    public CheckNext(int originX, int originY, int positionX, int positionY) {
        this.origin = new Pair(originX, originY);
        this.position = new Pair(positionX, positionY);
    }

    public CheckNext(Pair origin, Pair position) {
        this.origin = origin;
        this.position = position;
    }

    public Pair getOrigin() {
        return origin;
    }

    public void setOrigin(Pair origin) {
        this.origin = origin;
    }

    public Pair getPosition() {
        return position;
    }

    public void setPosition(Pair position) {
        this.position = position;
    }
}