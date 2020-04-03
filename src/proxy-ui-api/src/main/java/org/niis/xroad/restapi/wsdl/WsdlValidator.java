/**
 * The MIT License
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.xroad.restapi.wsdl;

import ee.ria.xroad.common.SystemProperties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.niis.xroad.restapi.exceptions.ErrorDeviation;
import org.niis.xroad.restapi.service.ExternalProcessRunner;
import org.niis.xroad.restapi.service.ProcessFailedException;
import org.niis.xroad.restapi.service.ProcessNotExecutableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * WsdlValidator as done in X-Road addons: wsdlvalidator
 */
@Slf4j
@Component
public class WsdlValidator {
    private final ExternalProcessRunner externalProcessRunner;
    @Getter
    private final String wsdlValidatorCommand;

    @Autowired
    public WsdlValidator(ExternalProcessRunner externalProcessRunner) {
        this.externalProcessRunner = externalProcessRunner;
        this.wsdlValidatorCommand = SystemProperties.getWsdlValidatorCommand();
    }

    /**
     * validate WSDL with user selected validator
     * @param wsdlUrl
     * @return List of validation warnings that could be ignored by choice
     * @throws WsdlValidatorNotExecutableException when validator is not found or
     * there are errors (not warnings, cant be ignored) when trying to execute the validator
     * @throws WsdlValidationFailedException when validation itself fails.
     * @throws InterruptedException if the thread running the validator is interrupted. <b>The interrupted thread has
     * already been handled with so you can choose to ignore this exception if you so please.</b>
     */
    public List<String> executeValidator(String wsdlUrl) throws WsdlValidatorNotExecutableException,
            WsdlValidationFailedException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        // validator not set - this is ok since validator is optional
        if (StringUtils.isEmpty(getWsdlValidatorCommand())) {
            return warnings;
        }

        if (StringUtils.isEmpty(wsdlUrl)) {
            throw new IllegalArgumentException("wsdl url cannot be null or empty");
        }

        try {
            ExternalProcessRunner.ProcessResult processResult = externalProcessRunner
                    .executeAndThrowOnFailure(getWsdlValidatorCommand(), wsdlUrl);
            return processResult.getProcessOutput();
        } catch (ProcessNotExecutableException e) {
            throw new WsdlValidatorNotExecutableException(e);
        } catch (ProcessFailedException e) {
            throw new WsdlValidationFailedException(e.getErrorDeviation().getMetadata());
        }
    }

    /**
     * Thrown if WSDL validation fails
     */
    public static class WsdlValidationFailedException extends InvalidWsdlException {
        public WsdlValidationFailedException(List<String> metadata) {
            super(metadata);
        }
    }

    /**
     * Thrown if WSDL validation fails
     */
    public static class WsdlValidatorNotExecutableException extends WsdlValidationException {

        public static final String ERROR_WSDL_VALIDATOR_NOT_EXECUTABLE = "wsdl_validator_not_executable";

        public WsdlValidatorNotExecutableException(Throwable t) {
            super(t, new ErrorDeviation(ERROR_WSDL_VALIDATOR_NOT_EXECUTABLE));
        }
    }
}
