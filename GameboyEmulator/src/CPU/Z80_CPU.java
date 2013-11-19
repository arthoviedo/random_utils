package CPU;

public class Z80_CPU {
    final int reg_a = 0;
    final int reg_b = 1;
    final int reg_c = 2;
    final int reg_d = 3;
    final int reg_e = 4;
    final int reg_h = 5;
    final int reg_l = 6;
    
    int [] registers = new int[9];
    
    
    // program counter, stack pointer
    int pc, sp;               

    // flags
    boolean flag_zero;          // if last operation produced 0
    boolean flag_operation;     // if last operation was substraction
    boolean flag_half_carry;    // if last half of the result did overflow (>15)
    boolean flag_carry;         // if last result did overflow (>255 OR < 0)
    
   
    
    
    Clock clock=new Clock();       // The amount of time that the CPU has run in total;
    Clock lastInstruccion = new Clock();
    
    public Z80_CPU() {
        
    }
    
    void resetFlags (){
        flag_zero = false;
        flag_operation = false;
        flag_half_carry = false;
        flag_carry = false;
    }
    
}
