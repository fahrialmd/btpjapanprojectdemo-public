sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'invoice/test/integration/FirstJourney',
		'invoice/test/integration/pages/SupplierInvoiceList',
		'invoice/test/integration/pages/SupplierInvoiceObjectPage'
    ],
    function(JourneyRunner, opaJourney, SupplierInvoiceList, SupplierInvoiceObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('invoice') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheSupplierInvoiceList: SupplierInvoiceList,
					onTheSupplierInvoiceObjectPage: SupplierInvoiceObjectPage
                }
            },
            opaJourney.run
        );
    }
);