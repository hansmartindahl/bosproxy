# BOS Proxy

A lightweight REST API proxy for the [Braiins OS](https://braiins.com/os/start) (BOS) gRPC API. Use it to control and monitor Braiins-compatible Bitcoin miners over HTTP/JSON instead of gRPC.

## Features

- **REST over gRPC** — All BOS gRPC services exposed as JSON REST endpoints
- **Authentication** — Login and token-based auth; token is forwarded to the miner on each request
- **One proxy per miner** — Designed to run one instance per miner (e.g. one Docker container per device) for simple deployment and isolation

## API overview

| Area | Endpoints |
|------|-----------|
| **Authentication** | Login, set password |
| **Actions** | Start, stop, pause/resume mining, restart, reboot, locate device |
| **Miner** | Status, details, stats, errors, hashboards, support archive, enable/disable hashboards |
| **Performance** | Tuner state, power/hashrate targets, DPS, performance mode, tuned profiles |
| **Configuration** | Miner configuration, constraints |
| **Network** | Get/set network configuration, network info |
| **Pool** | Pool groups (get, create, update, remove, set) |
| **Cooling** | Cooling state, set cooling mode |
| **License** | License state |

The repository includes a [Bruno](https://www.usebruno.com/) collection in the `bruno/` folder so you can explore and call all endpoints from the IDE.

## Requirements

- **Java 21**
- A Braiins OS miner (or compatible device) with gRPC enabled and reachable on your network

## Configuration

The proxy connects to a single miner. Configure the gRPC target in either:

**Option 1: Environment variables (recommended for Docker)**

| Variable | Description |
|----------|-------------|
| `GRPC_SERVER_ADDRESS` | Miner hostname or IP |
| `GRPC_SERVER_PORT` | gRPC port (default on BOS is `50051`) |

**Option 2: `application.properties`**

```properties
grpc.server.address=192.168.20.110
grpc.server.port=50051
```

Environment variables override properties.

## Build

```bash
./mvnw package
```

## Run locally

```bash
./mvnw spring-boot:run
```

Or run the JAR (set env vars or adjust `application.properties` first):

```bash
java -jar target/bosproxy-0.0.1-SNAPSHOT.jar
```

The API is available at `http://localhost:8080`.

## Run with Docker

Build the image:

```bash
docker build -t bosproxy .
```

Run (replace with your miner’s address and port):

```bash
docker run -p 8080:8080 \
  -e GRPC_SERVER_ADDRESS=192.168.20.110 \
  -e GRPC_SERVER_PORT=50051 \
  bosproxy
```

For multiple miners, run one container per miner with the appropriate `GRPC_SERVER_ADDRESS` and `GRPC_SERVER_PORT` (and optionally different host ports, e.g. `-p 8081:8080`).

## Usage

1. **Login** — `POST /authentication/login` with `{"username":"root","password":"<password>"}`. The response includes a `token`.
2. **Authenticated requests** — Send the token in the `Authorization` header for all other endpoints.

Example with curl:

```bash
# Login
TOKEN=$(curl -s -X POST http://localhost:8080/authentication/login \
  -H "Content-Type: application/json" \
  -d '{"username":"root","password":"root"}' | jq -r '.token')

# Get miner status
curl -s -H "Authorization: $TOKEN" http://localhost:8080/miner/get-miner-status
```

## License

See [LICENSE](LICENSE) in this repository.
