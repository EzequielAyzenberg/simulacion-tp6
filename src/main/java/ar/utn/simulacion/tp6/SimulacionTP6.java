package ar.utn.simulacion.tp6;

import java.util.Random;

public class SimulacionTP6 {
	private int P;
	private int D;
	private int TPCD;
	private int TPLL;
	private int TPFD;
	private int T;
	private int TF;
	private int IA;
	private int VG;
	private int CP;
	private int IMP;
	private int CDP;
	private int CPECG;
	private int CPESG;
	private int PC;
	private Random rnd = new Random();
	private int HV = Integer.MAX_VALUE;
	private int CTP;
	private int PPECG;
	private int PPESG;

	public SimulacionTP6(int P, int D) {
		this.P = P;
		this.D = D;

		CI();

		while (T < TF) {
			if (TPLL <= TPCD && TPLL <= TPFD) {
				T = TPLL;
				IA();
				TPLL = T + IA;
				CP();
				CTP += CP; 
				if (TPCD < TPFD) {
					EVADEN_CON_GUARDIA();
				} else {
					EVADEN_SIN_GUARDIA();
				}
			} else {
				if (TPCD < TPFD) {
					T = TPCD;
					VG();
					TPFD = T + VG;
					TPCD = HV;
				} else {
					T = TPFD;
					TPCD = T + D;
					TPFD = HV;
				}
			}
		}
		
		PPECG = (CPECG * 100) / CTP;
		PPESG = (CPESG * 100) / CTP;
		
		System.out.println("D = "+D+", P = "+P+": "
				+"PPECG = "+PPECG+", PPESG: "+PPESG+""
						+ ", CDP: "+CDP);
	}

	private void CI() {
		TPCD = D;
		TPFD = HV;
		TPLL = 0;
		T = 0;
		TF = 99999;
	}

	private void VG() {
		VG = new Double(15 - 10 * rnd.nextDouble()).intValue();
	}

	private void EVADEN_SIN_GUARDIA() {
		CPESG += CP;
		for (int i=1; i<=CP; i++) {
			IMPORTE();
			CDP+=IMP;
		}
	}

	private void EVADEN_CON_GUARDIA() {
		if (CP >= 15) {
			int EV = CP / 3;
			for (int i=1; i<=EV; i++) {
				IMPORTE();
				if (IMP >= 7) {
					CDP += IMP;
					CPECG++;
				}
			}
		}
	}

	private void IMPORTE() {
		PC();
		if (PC > 15) {
			IMP = P;
		} else if (PC > 10) {
			IMP = P + 2;
		} else {
			IMP = P + 4;
		}
	}

	private void PC() {
		PC = new Double(Math.sqrt(1/3+7500*(rnd.nextDouble()))-50).intValue();
	}

	private void CP() {
		CP = 30 - new Double(29 * rnd.nextDouble()).intValue();
	}

	private void IA() {
		IA = new Double(15 - 10 * rnd.nextDouble()).intValue();
	}
}
