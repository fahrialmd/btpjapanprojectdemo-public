using MainService as service from '../../srv/main-service';

// Disable delete capability
annotate service.POMapping with @(Capabilities: {DeleteRestrictions: {Deletable: false}});

annotate service.POMapping with @(
    UI.SelectionFields: [
        original_po,
        original_po_item,
        replica_po,
        replica_po_item,
        company_code,
        supplier,
    ],
    UI.LineItem       : [
        {
            $Type: 'UI.DataField',
            Value: original_po,
        },
        {
            $Type: 'UI.DataField',
            Value: original_po_item,
        },
        {
            $Type: 'UI.DataField',
            Value: replica_po,
        },
        {
            $Type: 'UI.DataField',
            Value: replica_po_item,
        },
        {
            $Type: 'UI.DataField',
            Value: company_code,
        },
        {
            $Type: 'UI.DataField',
            Value: supplier,
        }
    ],
);

annotate service.POMapping with {
    original_po @Common.Label: '{i18n>OriginalPoNumber}'
};

annotate service.POMapping with {
    original_po_item @Common.Label: '{i18n>OriginalPoItemNumber}'
};

annotate service.POMapping with {
    replica_po @Common.Label: '{i18n>ReplicatedPoNumber}'
};

annotate service.POMapping with {
    replica_po_item @Common.Label: '{i18n>ReplicatedPoItemNumber}'
};

annotate service.POMapping with {
    company_code @Common.Label: '{i18n>CompanyCode}'
};

annotate service.POMapping with {
    supplier @Common.Label: '{i18n>Supplier}'
};
