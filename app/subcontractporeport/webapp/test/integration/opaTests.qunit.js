sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'subcontractporeport/test/integration/FirstJourney',
		'subcontractporeport/test/integration/pages/POMappingList',
		'subcontractporeport/test/integration/pages/POMappingObjectPage'
    ],
    function(JourneyRunner, opaJourney, POMappingList, POMappingObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('subcontractporeport') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onThePOMappingList: POMappingList,
					onThePOMappingObjectPage: POMappingObjectPage
                }
            },
            opaJourney.run
        );
    }
);