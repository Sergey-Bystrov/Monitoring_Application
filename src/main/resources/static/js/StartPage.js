document.addEventListener('DOMContentLoaded', function() {
    function updateData() {
        fetch('/update_info')
            .then(response => response.json())
            .then(data => {
                document.getElementById('temperatureValue').textContent = data.temperatureValue;
                document.getElementById('humidityValue').textContent = data.humidityValue;
                document.getElementById('weightValue').textContent = data.weightValue;
                document.getElementById('timeValue').textContent = data.timeValue;
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    setInterval(updateData, 15000); // Update every 15 seconds

    // Initial call to populate data right away
    updateData();
});