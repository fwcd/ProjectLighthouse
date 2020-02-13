package lighthouse.spaceinvaders.model;

public class HealthPoints {
    private final int maxValue;
    private final int value;
    
    public HealthPoints(int maxValue) {
        this(maxValue, maxValue);
    }
    
    public HealthPoints(int maxValue, int value) {
        if (value > maxValue) {
            throw new IllegalArgumentException(value + " cannot be larger than the max HP " + maxValue);
        }
        this.maxValue = maxValue;
        this.value = value;
    }
    
    public HealthPoints decrease() { return new HealthPoints(maxValue, Math.max(0, value - 1)); }
    
    public boolean areFull() { return value >= maxValue; }
    
    public boolean areEmpty() { return value <= 0; }
    
    public int getValue() { return value; }
    
    public int getMaxValue() { return maxValue; }
    
    public double getPercent() { return value / (double) maxValue; }
}
