import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class ControlUnitTest extends FreeSpec with ChiselScalatestTester {

  "Control Unit" in {
    test(new ControlUnit) { dut =>

      // R-type
      dut.io.opcode.poke("b0110011".U)

      dut.io.regWrite.expect(true.B)
      dut.io.memRead.expect(false.B)
      dut.io.memWrite.expect(false.B)
      dut.io.memToReg.expect(false.B)
      dut.io.aluSrc.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.jal.expect(false.B)
      dut.io.jalr.expect(false.B)

      // Load
      dut.io.opcode.poke("b0000011".U)

      dut.io.regWrite.expect(true.B)
      dut.io.memRead.expect(true.B)
      dut.io.memWrite.expect(false.B)
      dut.io.memToReg.expect(true.B)
      dut.io.aluSrc.expect(true.B)

      // Store
      dut.io.opcode.poke("b0100011".U)

      dut.io.regWrite.expect(false.B)
      dut.io.memRead.expect(false.B)
      dut.io.memWrite.expect(true.B)
      dut.io.aluSrc.expect(true.B)
    }
  }
}