package CPU;

public class Functions {
    public interface Operation{
        public void eval();
    }
    
    //----------------------------------
    //Basic operations
    //----------------------------------
    
    public abstract class BaseOperation implements Operation{
        Z80_CPU cpu;
        
        void advanceTime() {
            cpu.lastInstruccion.m = 1;
            cpu.lastInstruccion.t = 4;
        }
    }
    
    public abstract class BaseOperation2Registers extends BaseOperation {
    	int r1;
    	int r2;
    	BaseOperation2Registers(int r1, int r2) {
    		this.r1 = r1;
    		this.r2 = r2;
    	}
    }
    
    // ADD with carry
    public class ADC extends BaseOperation2Registers {
       
    	ADC(int r1, int r2) {
			super(r1, r2);
			// TODO Auto-generated constructor stub
		}

		@Override
        public void eval() {
            cpu.registers[r1] += cpu.registers[r2] + (cpu.flag_carry?1:0);
            if (cpu.registers[r1] == 0 ){
                cpu.flag_zero = true;
            }
            if (cpu.registers[r1] > 15){
                cpu.flag_half_carry = true;
            }
            if (cpu.registers[r1] > 255) {
                cpu.flag_carry = true;
            }
            cpu.flag_operation = false;
            cpu.registers[r1] &= 255;
            advanceTime();
        }
        
    }
    
    //(ADD r1, r2) leaves result in r1
    public class ADD extends BaseOperation2Registers implements Operation {

        public ADD(int r1, int r2){
        	super(r1, r2);
        }
        
        @Override
        public void eval() {
            cpu.registers[r1] += cpu.registers[r2];
            if (cpu.registers[r1] == 0 ){
                cpu.flag_zero = true;
            }
            if (cpu.registers[r1] > 15){
                cpu.flag_half_carry = true;
            }
            if (cpu.registers[r1] > 255) {
                cpu.flag_carry = true;
            }
            cpu.flag_operation = false;
            cpu.registers[r1] &= 255;
            advanceTime();
        }
        
    }

    // Compares r1 with r2 setting the carry flag if r2 is bigger.
	public class CP extends BaseOperation2Registers {
	
		CP(int r1, int r2) {
			super(r1, r2);
		}
	
		@Override
		public void eval() {
			int temp = cpu.registers[r1];
			temp -= cpu.registers[r2];
			cpu.flag_operation = true;
			if (temp == 0){
				cpu.flag_zero = true;
			}
			if (temp < 0) {
				cpu.flag_carry = true;
			}
			advanceTime();
		}
	}
	
	public class SUB extends BaseOperation2Registers {
		SUB(int r1, int r2){
			super(r1, r2);
		}
		@Override
		public void eval () {
			cpu.registers[r1] -= cpu.registers[r2];
			if(cpu.registers[r1] == 0) {
				cpu.flag_zero = true;
			}
			if(cpu.registers[r1] < 0) {
				cpu.flag_carry = true;
			}
			cpu.registers[r1] &=255;
			advanceTime();
				
		}
		
	}
	
	// Subtracts with carry
	public class SBC extends BaseOperation2Registers {
		SBC(int r1, int r2){
			super(r1, r2);
		}
		@Override
		public void eval () {
			cpu.registers[r1] -= cpu.registers[r2];
			if (cpu.flag_carry) {
				cpu.registers[r1] -= 1;
			}
			if(cpu.registers[r1] == 0) {
				cpu.flag_zero = true;
			}
			if(cpu.registers[r1] < 0) {
				cpu.flag_carry = true;
			}
			cpu.registers[r1] &=255;
			advanceTime();
				
		}
		
	}

	public class NOP extends BaseOperation implements Operation {
		@Override
		public void eval() {
			advanceTime();
		}
	}
    
}
