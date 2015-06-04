package search.random;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

import ec.EvolutionState;
import ec.util.Code;
import ec.util.DecodeReturn;
import ec.util.MersenneTwisterFast;
import ec.vector.Gene;

public class EncounterGeneRandom extends Gene 
{	
	private static final long serialVersionUID = 1L;	
	
	public double minCAPY=-20;
	public double maxCAPY=20;
	public double minCAPR=0;
	public double maxCAPR=20;
	public double minCAPTheta=-180;
	public double maxCAPTheta=180;
	public double minCAPVy=-2;
	public double maxCAPVy=2;
	public double minCAPGs=2;
	public double maxCAPGs=10;
	public double minCAPBearing=-180;
	public double maxCAPBearing=180;
	public double minCAPT=10;
	public double maxCAPT=30;
	
	public double CAPY=0;
	public double CAPR=0;
	public double CAPTheta=0;
	public double CAPVy=0;
	public double CAPGs=0;
	public double CAPBearing=0;
	public double CAPT=0;

	
	public EncounterGeneRandom initialize(MersenneTwisterFast rdn) 
	{		
		CAPY=minCAPY+rdn.nextDouble(true, true) * (maxCAPY-minCAPY);
		CAPR=minCAPR+rdn.nextDouble(true, true) * (maxCAPR-minCAPR);
		CAPTheta=minCAPTheta+rdn.nextDouble(true, true) * (maxCAPTheta-minCAPTheta);
		CAPVy=minCAPVy+rdn.nextDouble(true, true) * (maxCAPVy-minCAPVy);
		CAPGs=minCAPGs+rdn.nextDouble(true, true) * (maxCAPGs-minCAPGs);
		CAPBearing=minCAPBearing+rdn.nextDouble(true, true) * (maxCAPBearing-minCAPBearing);
		CAPT=minCAPT+rdn.nextDouble(true, true) * (maxCAPT-minCAPT);		
		return this;
	}
	
	public int hashCode() 
	{
		return Objects.hash(CAPY,CAPR,CAPTheta,CAPVy,CAPGs,CAPBearing,CAPT);
	}
	
	public boolean equals(Object other)
	{
		if (this==other)
		{
			return true;
		}
		
		if(other !=null && other.getClass()==EncounterGeneRandom.class)
		{
			EncounterGeneRandom otherGene = (EncounterGeneRandom)other;
			if( this.CAPY==otherGene.CAPY
				&& this.CAPR == otherGene.CAPR
				&& this.CAPTheta == otherGene.CAPTheta
				&& this.CAPVy==otherGene.CAPVy
				&& this.CAPGs==otherGene.CAPGs
				&& this.CAPBearing==otherGene.CAPBearing
				&& this.CAPT==otherGene.CAPT)
			{
				return true;
			}
		}
		return false;		
	}
	
	public String toString()
	{
		return ">"+ CAPY + " "+CAPR + " "+ CAPTheta + " " +CAPVy+ " " +CAPGs+ " " +CAPBearing+ " " +CAPT+" ";
	}

//	public void readGeneFromStringForHumans(String string, EvolutionState state) 
//	{ 
//		// the string is in the form of: CAPY CAPR CAPTheta CAPVy CAPGs CAPBearing CAPT
//		string = string.trim();
//		String[] strings = string.split("\\s+");
//		CAPY = Double.parseDouble(strings[0]);
//		CAPR = Double.parseDouble(strings[1]);
//		CAPTheta = Double.parseDouble(strings[2]);		
//		CAPVy = Double.parseDouble(strings[3]);
//		CAPGs = Double.parseDouble(strings[4]);
//		CAPBearing = Double.parseDouble(strings[5]);		
//		CAPT = Double.parseDouble(strings[6]);
//	}
	
//	public String printGeneToStringForHumans() { return ">"+ CAPY + " "+CAPR + " "+ CAPTheta + " " +CAPVy+ " " +CAPGs+ " " +CAPBearing+ " " +CAPT+ " "; }
//	public String printGeneToString()
//	{
//		return ">" + Code.encode(CAPY)+" " + Code.encode(CAPR)+" "+ Code.encode(CAPTheta) + " " + Code.encode(CAPVy)+ " " + Code.encode(CAPGs)+ " " + Code.encode(CAPBearing)+ " " + Code.encode(CAPT)+ " ";
//	}
//	
//	public void readGeneFromString(String string, EvolutionState state) 
//	{
//		string = string.trim().substring(1); // get rid of the ">"
//		DecodeReturn dr = new DecodeReturn(string);
//		Code.decode(dr); 
//		CAPY = dr.d;
//		Code.decode(dr); 
//		CAPR = dr.d;
//		Code.decode(dr); 
//		CAPTheta = dr.d; // no error checking
//		Code.decode(dr); 
//		CAPVy = dr.d;
//		Code.decode(dr); 
//		CAPGs = dr.d;		
//		Code.decode(dr); 
//		CAPBearing = dr.d;
//		Code.decode(dr); 
//		CAPT = dr.d;	
//	}	
	
//	public void writeGene(EvolutionState state, DataOutput out) throws IOException
//	{
//		out.writeDouble(CAPY);
//		out.writeDouble(CAPR);
//		out.writeDouble(CAPTheta);
//		out.writeDouble(CAPVy);
//		out.writeDouble(CAPGs);
//		out.writeDouble(CAPBearing);
//		out.writeDouble(CAPT);
//	}
//	
//	public void readGene(EvolutionState state, DataInput in) throws IOException
//	{
//		CAPY = in.readDouble();
//		CAPR = in.readDouble();
//		CAPTheta = in.readDouble();
//		CAPVy = in.readDouble();
//		CAPGs = in.readDouble();
//		CAPBearing = in.readDouble();
//		CAPT = in.readDouble();
//	}

	@Override
	public void reset(EvolutionState state, int thread) {
		
	}	
	
}