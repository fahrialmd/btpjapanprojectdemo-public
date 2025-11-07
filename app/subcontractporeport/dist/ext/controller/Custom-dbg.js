sap.ui.define([
    "sap/m/MessageToast",
    "sap/m/MessageBox"
], function (MessageToast, MessageBox) {
    'use strict';

    return {
        startReplicateSubcontractPo: async function () {
            MessageToast.show("Starting replication...");

            try {
                await this.getEditFlow().invokeAction("startReplicatingPO", {
                    model: this.getModel()
                });
                MessageToast.show("Success");
            } catch (error) {
                MessageToast.show("Failed: " + (error.message || "Unknown error"));
            }
        }
    };
});