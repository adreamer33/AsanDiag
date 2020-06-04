package ir.asandiag.obd.model;

public class SensorItem {
    private int id;
    private String name;
    private String description;
    private float maxAmount;
    private float minAmount;
    private float avgAmount;
    private float currentAmount;
    private float naAmount;
    private float rangeMin;
    private float rangeMax;

    public SensorItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public float getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(float minAmount) {
        this.minAmount = minAmount;
    }

    public float getAvgAmount() {
        return avgAmount;
    }

    public void setAvgAmount(float avgAmount) {
        this.avgAmount = avgAmount;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public float getNaAmount() {
        return naAmount;
    }

    public void setNaAmount(float naAmount) {
        this.naAmount = naAmount;
    }

    public float getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(float rangeMin) {
        this.rangeMin = rangeMin;
    }

    public float getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(float rangeMax) {
        this.rangeMax = rangeMax;
    }
}
