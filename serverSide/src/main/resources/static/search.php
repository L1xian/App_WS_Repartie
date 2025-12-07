<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background: #0f172a;
            min-height: 100vh;
            padding: 20px;
            color: #e2e8f0;
        }

        .container {
            background: #1e293b;
            padding: 40px;
            max-width: 1400px;
            margin: 2rem auto;
            border: 1px solid #334155;
        }

        .header {
            margin-bottom: 40px;
            padding-bottom: 20px;
            border-bottom: 2px solid #334155;
        }

        h1 {
            color: white;
            margin-bottom: 8px;
            font-size: 28px;
            font-weight: 700;
        }

        .subtitle {
            color: #94a3b8;
            font-size: 15px;
        }

        .results-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 20px;
        }

        .hotel-card {
            background: #0f172a;
            border: 1px solid #334155;
            padding: 24px;
            border-radius: 8px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .hotel-header h3 {
            color: white;
            margin-bottom: 8px;
            font-size: 20px;
        }

        .hotel-details {
            color: #94a3b8;
            font-size: 14px;
            margin-top: 16px;
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .hotel-actions {
            margin-top: 20px;
        }

        .no-results, .error-message {
            text-align: center;
            padding: 60px 20px;
            color: #94a3b8;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #3b82f6;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
        }

        .btn-book {
            background: #3b82f6;
            color: white;
            width: 100%;
        }

        .btn-book:hover {
            background: #2563eb;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Search Results</h1>
    </div>

    <div id="results">
        <?php
        // --- Database Configuration ---
        $servername = "localhost";
        $username = "root";
        $password = ""; // Default password for EasyPHP is often empty
        $dbname = "hotel_booking";

        // --- Create Connection ---
        $conn = new mysqli($servername, $username, $password, $dbname);

        // --- Check Connection ---
        if ($conn->connect_error) {
            echo "<div class='error-message'>Connection failed: " . $conn->connect_error . "</div>";
        } else {
            if (isset($_GET['location'])) {
                $location = trim($_GET['location']);

                if (!empty($location)) {
                    // --- Prepare and Bind SQL Statement ---
                    $stmt = $conn->prepare("SELECT id, name, location, stars, rooms FROM hotels WHERE location LIKE ?");
                    $searchTerm = "%" . $location . "%";
                    $stmt->bind_param("s", $searchTerm);

                    $stmt->execute();

                    // --- Manually bind results (for older PHP versions without mysqlnd) ---
                    $stmt->bind_result($id, $name, $location_db, $stars, $rooms);

                    $stmt->store_result(); // Buffer the results

                    if ($stmt->num_rows > 0) {
                        echo "<p class='subtitle'>Found " . $stmt->num_rows . " hotel(s) in '" . htmlspecialchars($location) . "'.</p><br>";
                        echo "<div class='results-grid'>";

                        // --- Loop through buffered results ---
                        while($stmt->fetch()) {
                            echo "<div class='hotel-card'>";
                            echo "<div>"; // Flex container
                            echo "<div class='hotel-header'><h3>" . htmlspecialchars($name) . "</h3></div>";
                            echo "<div class='hotel-details'>";
                            echo "<div><strong>Location:</strong> " . htmlspecialchars($location_db) . "</div>";
                            echo "<div><strong>Stars:</strong> " . str_repeat('★', $stars) . str_repeat('☆', 5 - $stars) . "</div>";
                            echo "<div><strong>Rooms:</strong> " . htmlspecialchars($rooms) . "</div>";
                            echo "</div>";
                            echo "</div>";
                            echo "<div class='hotel-actions'>";
                            echo "<a href='reserve.html?hotelId=" . urlencode($id) . "&hotelName=" . urlencode($name) . "' class='btn-book'>Book Now</a>";
                            echo "</div>";
                            echo "</div>";
                        }
                        echo "</div>";
                    } else {
                        echo "<div class='no-results'>No hotels found in '" . htmlspecialchars($location) . "'.</div>";
                    }
                    $stmt->close();
                } else {
                    echo "<div class='no-results'>Please enter a location to search.</div>";
                }
            } else {
                echo "<div class='no-results'>Please enter a location to search.</div>";
            }
            $conn->close();
        }
        ?>
        <br>
        <a href="search.html">New Search</a>
    </div>
</div>
</body>
</html>