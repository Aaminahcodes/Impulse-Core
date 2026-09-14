import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class immGenTest extends FreeSpec with ChiselScalatestTester {

  "Immediate Generator" in {
    test(new immGen) { dut =>

      // I-type: addi x3, x0, 4
      dut.io.instr.poke("h00400193".U)
      dut.io.immd_se.expect(4.U)

      // S-type: sw x1, 4(x0)
      dut.io.instr.poke("h00102223".U)
      dut.io.immd_se.expect(4.U)

      // U-type: lui x1, 1
      dut.io.instr.poke("h000010B7".U)
      dut.io.immd_se.expect("h00001000".U)
    }
  }
}