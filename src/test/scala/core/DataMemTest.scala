import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class DataMemTest extends FreeSpec with ChiselScalatestTester {

  "Data Memory" in {
    test(new DataMem) { dut =>

      // Write
      dut.io.address.poke(4.U)
      dut.io.writeData.poke(100.U)
      dut.io.memWrite.poke(true.B)
      dut.io.memRead.poke(false.B)

      dut.clock.step()

      // Read
      dut.io.memWrite.poke(false.B)
      dut.io.memRead.poke(true.B)

      dut.io.address.poke(4.U)
      dut.io.readData.expect(100.U)
    }
  }
}