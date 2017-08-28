package model.utils;

import oracle.jbo.AttributeList;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
public class BasicEntity extends EntityImpl {
    public BasicEntity() {
        super();
    }


    @Override
    protected void doDML(int i, TransactionEvent transactionEvent) {
        // TODO Implement this method
        if (i != DML_DELETE) {
            String[] attributeNames = this.getAttributeNames();
            for (String attName : attributeNames) {
                Object attValue = this.getAttribute(attName);
                if (attValue != null && attValue instanceof String) {
                    attValue = attValue.toString().trim();
                    this.setAttribute(attName, attValue);
                }
            }
        }
        if (i == DML_UPDATE) {
            try {
                ModelUtils utils = new ModelUtils();
                setAttribute("UpdatedBy", utils.getCurrentUser());
                setAttribute("UpdatedDate", utils.getCurrentTimestamp());
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        }
        super.doDML(i, transactionEvent);
    }

    @Override
    protected void create(AttributeList attributeList) {
        // TODO Implement this method
        try {
            ModelUtils utils = new ModelUtils();
            setAttribute("CreatedBy", utils.getCurrentUser());
            setAttribute("CreatedDate", utils.getCurrentTimestamp());
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        super.create(attributeList);
    }

}
