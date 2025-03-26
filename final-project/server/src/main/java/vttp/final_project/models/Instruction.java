package vttp.final_project.models;

public class Instruction {
    private int step;
    private String instruction;
    private String equipmentName;
    
    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
