class GridElement 
{
    // ================= VARIABLES & CONSTANTS =================
    
    // The value at this location
    private int value;
    
    // The status of this location
    private boolean status;
    
    // Status constants
    private final boolean HIDDEN = false;
    private final boolean KNOWN = true;
    
    // ================= CONSTRUCTOR =================
    
    public GridElement(int val) 
    {
        value = val;
    }
    
    // ================= GETTERS AND SETTERS =================
    
    public int getValue() 
    {
        return value;
    }
    
    public boolean getStatus()
    {
        return status;
    }
    
    public void setStatus(boolean state)
    {
        status = state;
    }
}