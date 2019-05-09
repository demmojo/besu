/*
 * Copyright 2018 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.ethereum.jsonrpc.internal.response;

import tech.pegasys.pantheon.util.enode.EnodeURL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JsonRpcError {
  // Standard errors
  PARSE_ERROR(-32700, "Parse error"),
  INVALID_REQUEST(-32600, "Invalid Request"),
  METHOD_NOT_FOUND(-32601, "Method not found"),
  INVALID_PARAMS(-32602, "Invalid params"),
  INTERNAL_ERROR(-32603, "Internal error"),
  METHOD_NOT_ENABLED(-32604, "Method not enabled"),

  // P2P related errors
  P2P_DISABLED(-32000, "P2P has been disabled. This functionality is not available"),
  P2P_NETWORK_NOT_RUNNING(-32000, "P2P network is not running"),

  // Filter & Subscription Errors
  FILTER_NOT_FOUND(-32000, "Filter not found"),
  LOGS_FILTER_NOT_FOUND(-32000, "Logs filter not found"),
  SUBSCRIPTION_NOT_FOUND(-32000, "Subscription not found"),
  NO_MINING_WORK_FOUND(-32000, "No mining work available yet"),

  // Transaction validation failures
  NONCE_TOO_LOW(-32001, "Nonce too low"),
  INVALID_TRANSACTION_SIGNATURE(-32002, "Invalid signature"),
  INTRINSIC_GAS_EXCEEDS_LIMIT(-32003, "Intrinsic gas exceeds gas limit"),
  TRANSACTION_UPFRONT_COST_EXCEEDS_BALANCE(-32004, "Upfront cost exceeds account balance"),
  EXCEEDS_BLOCK_GAS_LIMIT(-32005, "Transaction gas limit exceeds block gas limit"),
  INCORRECT_NONCE(-32006, "Incorrect nonce"),
  TX_SENDER_NOT_AUTHORIZED(-32007, "Sender account not authorized to send transactions"),
  CHAIN_HEAD_WORLD_STATE_NOT_AVAILABLE(-32008, "Initial sync is still in progress"),
  // Miner failures
  COINBASE_NOT_SET(-32010, "Coinbase not set. Unable to start mining without a coinbase"),
  NO_HASHES_PER_SECOND(-32011, "No hashes being generated by the current node"),

  // Wallet errors
  COINBASE_NOT_SPECIFIED(-32000, "Coinbase must be explicitly specified"),

  // Debug failures
  PARENT_BLOCK_NOT_FOUND(-32000, "Parent block not found"),

  // Permissioning/Account whitelist errors
  ACCOUNT_WHITELIST_NOT_ENABLED(-32000, "Account whitelisting has not been enabled"),
  ACCOUNT_WHITELIST_EMPTY_ENTRY(-32000, "Request contains an empty list of accounts"),
  ACCOUNT_WHITELIST_INVALID_ENTRY(-32000, "Request contains an invalid account"),
  ACCOUNT_WHITELIST_DUPLICATED_ENTRY(-32000, "Request contains duplicate accounts"),
  ACCOUNT_WHITELIST_EXISTING_ENTRY(-32000, "Cannot add an existing account to whitelist"),
  ACCOUNT_WHITELIST_ABSENT_ENTRY(-32000, "Cannot remove an absent account from whitelist"),

  // Permissioning/Node whitelist errors
  NODE_WHITELIST_NOT_ENABLED(-32000, "Node whitelisting has not been enabled"),
  NODE_WHITELIST_EMPTY_ENTRY(-32000, "Request contains an empty list of nodes"),
  NODE_WHITELIST_INVALID_ENTRY(-32000, "Request contains an invalid node"),
  NODE_WHITELIST_DUPLICATED_ENTRY(-32000, "Request contains duplicate nodes"),
  NODE_WHITELIST_EXISTING_ENTRY(-32000, "Cannot add an existing node to whitelist"),
  NODE_WHITELIST_MISSING_ENTRY(-32000, "Cannot remove an absent node from whitelist"),
  NODE_WHITELIST_FIXED_NODE_CANNOT_BE_REMOVED(
      -32000, "Cannot remove a fixed node (bootnode or static node) from whitelist"),

  // Permissioning/persistence errors
  WHITELIST_PERSIST_FAILURE(
      -32000, "Unable to persist changes to whitelist configuration file. Changes reverted"),
  WHITELIST_FILE_SYNC(
      -32000,
      "The permissioning whitelist configuration file is out of sync.  The changes have been applied, but not persisted to disk"),
  WHITELIST_RELOAD_ERROR(
      -32000,
      "Error reloading permissions file. Please use perm_getAccountsWhitelist and perm_getNodesWhitelist to review the current state of the whitelists"),
  PERMISSIONING_NOT_ENABLED(-32000, "Node/Account whitelisting has not been enabled"),
  NON_PERMITTED_NODE_CANNOT_BE_ADDED_AS_A_PEER(-32000, "Cannot add a non-permitted node as a peer"),

  // Permissioning/Authorization errors
  UNAUTHORIZED(-40100, "Unauthorized"),

  // Private transaction errors
  ENCLAVE_ERROR(-50100, "Error communicating with enclave"),
  UNIMPLEMENTED_PRIVATE_TRANSACTION_TYPE(-50100, "Unimplemented private transaction type"),
  PRIVATE_TRANSACTION_RECEIPT_ERROR(-50100, "Error generating the private transaction receipt"),
  VALUE_NOT_ZERO(-50100, "We cannot transfer ether in private transaction yet."),
  DECODE_ERROR(-50100, "Unable to decode the private signed raw transaction"),

  CANT_CONNECT_TO_LOCAL_PEER(-32100, "Cannot add local node as peer."),

  // Invalid input errors
  ENODE_ID_INVALID(-32000, EnodeURL.INVALID_NODE_ID_LENGTH);

  private final int code;
  private final String message;

  JsonRpcError(final int code, final String message) {
    this.code = code;
    this.message = message;
  }

  @JsonGetter("code")
  public int getCode() {
    return code;
  }

  @JsonGetter("message")
  public String getMessage() {
    return message;
  }

  @JsonCreator
  public static JsonRpcError fromJson(
      @JsonProperty("code") final int code, @JsonProperty("message") final String message) {
    for (final JsonRpcError error : JsonRpcError.values()) {
      if (error.code == code && error.message.equals(message)) {
        return error;
      }
    }
    return null;
  }
}
