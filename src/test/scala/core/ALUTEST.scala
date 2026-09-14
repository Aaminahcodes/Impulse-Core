import chisel3._
import chiseltest._
import org.scalatest.FreeSpec
import ALUOP._

class ALUTEST extends FreeSpec with ChiselScalatestTester {

  "ALU" in {
    test(new aluOP) { dut =>

      dut.io.in_A.poke(10.S)
      dut.io.in_B.poke(5.S)

      dut.io.aluop.poke(ALU_ADD)
      dut.io.output.expect(15.S)

      dut.io.aluop.poke(ALU_SUB)
      dut.io.output.expect(5.S)

      dut.io.aluop.poke(ALU_AND)
      dut.io.output.expect(0.S)

      dut.io.aluop.poke(ALU_OR)
      dut.io.output.expect(15.S)

      dut.io.aluop.poke(ALU_XOR)
      dut.io.output.expect(15.S)
    }
  }
}