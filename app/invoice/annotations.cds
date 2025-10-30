using MainService as service from '../../srv/main-service';
annotate service.SupplierInvoice with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : invoice_no,
            Label : 'invoice_no',
        },
        {
            $Type : 'UI.DataField',
            Value : fiscal_year,
            Label : 'fiscal_year',
        },
        {
            $Type : 'UI.DataField',
            Value : invoicing_party,
            Label : 'invoicing_party',
        },
        {
            $Type : 'UI.DataField',
            Value : company_code,
            Label : 'company_code',
        },
        {
            $Type : 'UI.DataField',
            Value : gross_invoice,
            Label : 'gross_invoice',
        },
        {
            $Type : 'UI.DataField',
            Value : document_currency_code,
        },
        {
            $Type : 'UI.DataField',
            Value : posting_date,
            Label : 'posting_date',
        },
        {
            $Type : 'UI.DataField',
            Value : invoice_date,
            Label : 'invoice_date',
        },
        {
            $Type : 'UI.DataField',
            Value : reference,
            Label : 'reference',
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.ShowPreviousMonth',
            Label : 'ShowPreviousMonth',
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.ShowCurrentMonth',
            Label : 'ShowCurrentMonth',
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.EntityContainer/ReplicateInvoices',
            Label : 'ReplicateInvoices',
        },
    ]
);

