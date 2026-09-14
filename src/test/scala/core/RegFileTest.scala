import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class RegFileTest extends FreeSpec with ChiselScalatestTester {

  "Register File" in {
    test(new RegFile) { dut =>

      dut.io.rs1.poke(0.U)
      dut.io.rs2.poke(0.U)
      dut.io.rdata1.expect(0.U)
      dut.io.rdata2.expect(0.U)

      dut.io.wen.poke(true.B)
      dut.io.waddr.poke(5.U)
      dut.io.wdata.poke(100.U)
      dut.clock.step()

      dut.io.wen.poke(false.B)
      dut.io.rs1.poke(5.U)

      dut.io.rdata1.expect(100.U)
    }
  }
}