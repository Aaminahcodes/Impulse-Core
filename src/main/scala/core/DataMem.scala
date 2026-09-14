import chisel3._

class DataMem extends Module {

  val io = IO(new Bundle {
    val address = Input(UInt(32.W))
    val writeData = Input(UInt(32.W))

    val memRead = Input(Bool())
    val memWrite = Input(Bool())

    val readData = Output(UInt(32.W))
  })

  val memory = Mem(256, UInt(32.W))

  io.readData := 0.U

  when(io.memWrite) {
    memory.write(io.address(9, 2), io.writeData)
  }

  when(io.memRead) {
    io.readData := memory(io.address(9, 2))
  }
}