package entities;

public class Material implements Comparable<Material>
{
    // Fields
    private String name;
    private Double value;
    private String unit;

    // Properties

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Constructors
    public Material()
    {
    }

    public Material(String name, Double value, String unit)
    {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    // Methods
    @Override
    public String toString()
    {
        return String.format("%s: %.1f %s", name, value, unit);
    }

    @Override
    public int compareTo(Material o) {
        return this.name.compareTo(o.name);
    }
}
