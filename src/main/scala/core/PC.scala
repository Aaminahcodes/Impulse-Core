import chisel3._

class PC extends Module {
  val io = IO(new Bundle {
    val nextPC  = Input(UInt(32.W))
    val pc      = Output(UInt(32.W))
    val pcPlus4 = Output(UInt(32.W))
  })

  val pcReg = RegInit(0.U(32.W))

  io.pc := pcReg
  io.pcPlus4 := pcReg + 4.U

  pcReg := io.nextPC
}