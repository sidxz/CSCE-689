@Override
public void visitFieldInsn(int opcode, String owner, String name, String desc) {
      switch (opcode) {
            case GETSTATIC:
		//your code here
                break;
            case PUTSTATIC:
            	//your code here
                break;
            case GETFIELD:
            	//your code here
                break;
            case PUTFIELD:
            	//your code here
		//this part is slightly more complicated
             default: break;
      	}
    mv.visitFieldInsn(opcode, owner, name, desc);
}    
