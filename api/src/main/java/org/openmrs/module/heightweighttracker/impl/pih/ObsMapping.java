package org.openmrs.module.heightweighttracker.impl.pih;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.module.heightweighttracker.mapper.BaseObs;

public abstract class ObsMapping implements BaseObs {

	private Obs obs;

	public ObsMapping(Obs obs) {
		super();
		
		this.obs = obs;
	}
	
	protected Obs getObs() {
		return obs;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.rwandahivflowsheet.web.controller.ObsMapper#isEmr()
	 */
	@Override
	public boolean isEmr() {
		return obs.getObsDatetime() != null;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.rwandahivflowsheet.web.controller.ObsMapper#isVoided()
	 */
	@Override
	public boolean isVoided() {
		if(!isEmr())
			return false;
		
		return getObs().isVoided();
	}


	public boolean isBlank() {
		return getObsDate() == null;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.rwandahivflowsheet.web.controller.ObsMapper#getObsDate()
	 */
	public Date getObsDate() {
		return obs.getObsDatetime();
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.rwandahivflowsheet.web.controller.ObsMapper#getDate()
	 */
	@Override
	public Date getDate() {
		return getObsDate();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.rwandahivflowsheet.web.controller.ObsMapper#getEncounter()
	 */
	@Override
	public Encounter getEncounter() {
		if(obs == null)
		{
			return null;
		}
		
		//we only want to return encounters of the flowsheet types as it is not possible to edit
		//encounters entered through the flowsheet
		if(obs.getEncounter() != null)
		{
			if(obs.getEncounter().getForm() != null && !obs.getEncounter().getForm().getRetired())
			{
				return obs.getEncounter();
			}
		}
		
		return null;
	}

	//  MLH TODO FIXME create a basic equals method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getObsDate() == null) ? 0 : getObsDate().hashCode());
		return result;
	}


	protected static boolean areObsDifferentValue(Obs one, Obs two) {
		if(one == null ^ two == null)
			return true;
		if((one != null && two != null) && one.getValueAsString(null) != null && !one.getValueAsString(null).equalsIgnoreCase(two.getValueAsString(null)))
			return true;
		
		return false;
	}
	
	protected static boolean areConceptsDifferentValue(Concept one, Concept two) {
		if(one == null ^ two == null)
			return true;
		if((one != null && two != null) && one.getConceptId() != two.getConceptId())
			return true;
		
		return false;
	}


}