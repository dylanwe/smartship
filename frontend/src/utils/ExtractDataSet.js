/**
 * Extract a dataset from given data
 * @param {Array} data - An array of data objects
 * @returns {Array} - An array of extracted dataset in the form of [time, value]
 */
export default function ExtractDataSet(data) {
    return data.map(x => {
        // Extract the dataset in the form of [time, value]
        return  [(x.time * 1000), x.val.toFixed(1)]
    });
}
