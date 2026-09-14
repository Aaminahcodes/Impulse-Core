import chisel3._
import chisel3.util._

class TOP(initFile: String) extends Module { //will contain Instructions.hex file while testing

  val io = IO(new Bundle {
    val pc= Output(UInt(32.W))
    val instruction= Output(UInt(32.W))
    val aluResult= Output(UInt(32.W))
    val writeBackData= Output(UInt(32.W))
  })

  // write all modules
  val pc= Module(new PC)
  val instMem= Module(new InstMem(initFile))
  val regFile=Module(new RegFile)
  val immGen=Module(new immGen)
  val control=Module(new ControlUnit)
  val aluCtrl=Module(new ALUControl)
  val alu=Module(new aluOP) //ALUOP is imported into it
  val branch=Module(new alubranch)
  val dataMem=Module(new DataMem)

  // Wire names of modules
  val pcValue= pc.io.pc
  val pcPlus4=pc.io.pcPlus4
  val instruction=instMem.io.inst

  //extracting diff instruction types from instruction value
  val opcode=instruction(6,0)
  val rd=instruction(11,7)
  val funct3=instruction(14,12)
  val rs1=instruction(19,15)
  val rs2=instruction(24,20)
  val funct7=instruction(30)

  val rdata1=regFile.io.rdata1
  val rdata2=regFile.io.rdata2
  val immediate=immGen.io.immd_se //(sign-extended)
  val aluOp=aluCtrl.io.aluOp //(ALUControl, tells which opcode to take)
  val aluOut=alu.io.output.asUInt //result of alu
  val memReadData=dataMem.io.readData
  val brTaken=branch.io.br_taken

  val writeBackData=Wire(UInt(32.W))
  val operandA=Wire(UInt(32.W))
  val operandB=Wire(UInt(32.W))
  val nextPC=Wire(UInt(32.W))

  // PC
  pc.io.nextPC:=nextPC

  // InstMem
  instMem.io.addr:=pcValue

  // ControlUnit
  control.io.opcode:=opcode

  // RegFile
  regFile.io.rs1:=rs1
  regFile.io.rs2:=rs2
  regFile.io.wen:=control.io.regWrite
  regFile.io.waddr:=rd
  regFile.io.wdata:=writeBackData

  // ImmGen
  immGen.io.instr:=instruction

  // ALUControl
  aluCtrl.io.opcode:=opcode
  aluCtrl.io.funct3:=funct3
  aluCtrl.io.funct7:=funct7

  // ALU
  alu.io.in_A:=operandA.asSInt
  alu.io.in_B:=operandB.asSInt
  alu.io.aluop:=aluOp

  // Branch unit (alubranch)
  branch.io.func3:=funct3
  branch.io.branch:=control.io.branch //compare rdata1 and rdata2 using funct3 to decide if the branch is taken. 
                                    //only true if control.branch is true.
  branch.io.data_1:=rdata1
  branch.io.data_2:=rdata2

  // DataMem
  dataMem.io.address:=aluOut
  dataMem.io.writeData:=rdata2
  dataMem.io.memRead:=control.io.memRead
  dataMem.io.memWrite:=control.io.memWrite

  // ALU operand A
  operandA:=Mux(control.io.lui,0.U,
              Mux(control.io.auipc,pcValue,rdata1))

  // ALU operand B
  operandB:=Mux(control.io.aluSrc,immediate,rdata2)

  // Write-back through mux
  writeBackData:=Mux(control.io.jal||control.io.jalr,pcPlus4,
                   Mux(control.io.memToReg,memReadData,aluOut))

  // Next-PC through mux
  val branchTarget=pcValue+immediate
  val jalTarget=pcValue+immediate
  val jalrTarget=(rdata1+immediate)&"hFFFFFFFE".U
  val branchTaken=control.io.branch && brTaken

  nextPC :=Mux(control.io.jalr,jalrTarget,
            Mux(control.io.jal,jalTarget,
            Mux(branchTaken, branchTarget,pcPlus4)))

  // Output
  io.pc:= pcValue
  io.instruction:=instruction
  io.aluResult :=aluOut
  io.writeBackData:=writeBackData
}