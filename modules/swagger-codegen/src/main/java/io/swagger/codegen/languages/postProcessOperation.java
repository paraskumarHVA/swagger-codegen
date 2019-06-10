package io.swagger.codegen.languages;

import io.swagger.codegen.CodegenOperation;

import java.util.ArrayList;
import java.util.List;

public class postProcessOperation {
    public static List<CodegenOperation> postProcessOperation(List<CodegenOperation> newOpList, CodegenOperation op) {
        boolean foundInNewList = false;
        for (CodegenOperation op1 : newOpList) {
            if (!foundInNewList) {
                if (op1.path.equals(op.path)) {
                    foundInNewList = true;
                    List<CodegenOperation> currentOtherMethodList = (List<CodegenOperation>) op1.vendorExtensions.get("x-codegen-otherMethods");
                    if (currentOtherMethodList == null) {
                        currentOtherMethodList = new ArrayList<CodegenOperation>();
                    }
                    op.operationIdCamelCase = op1.operationIdCamelCase;
                    currentOtherMethodList.add(op);
                    op1.vendorExtensions.put("x-codegen-otherMethods", currentOtherMethodList);
                }
            }
        }
        if (!foundInNewList) {
            newOpList.add(op);
        }

        return newOpList;
    }
}
